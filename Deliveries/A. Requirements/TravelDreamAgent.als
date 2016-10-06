module TravelDreamAgent

//--- Basic type signature declarations, facts and predicates ---
//The following signatures do not correspond to an entity in the class diagram.
//They have been defined because they occur in the body of one or more signatures
//and are therefore essential for Alloy to interpret the model.

//DayOfWeek represents a day in a week (Monday, Tuesday...)
enum DayOfWeek {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}

//The Date signature represents a date in terms of day, month, year and weekday.
//The numeric constraints for the day, month and year fields are stated right
//after the signature declaration; more complex ones are included in the
//plausibleDate fact.
sig Date {
	day: one Int,
	month: one Int,
	year: one Int,
	weekDay: one DayOfWeek
} {
	day >= 1
	month >= 1
	year >= 0
}

//The datePrecedes and dateFollows predicates are functional to the expression
//of some constraints in the project-specific signatures.
pred datePrecedes [d1, d2 : Date] {
	d1.year < d2.year or
	(d1.year = d2.year and d1.month < d2.month) or
	(d1.year = d2.year and d1.month = d2.month and d1.day < d2.day)
}

pred dateFollows [d1, d2 : Date] {
	d1.year > d2.year or
	(d1.year = d2.year and d1.month > d2.month) or
	(d1.year = d2.year and d1.month = d2.month and d1.day > d2.day)
}

//plausibleDate prevents two instances from representing the same unique date.
fact plausibleDate {
	no disj d1, d2 : Date | !(datePrecedes[d1, d2] or dateFollows[d1, d2])
}

//The Time signature models the representation of a time coordinate, composed
//of an hour and a minute field. The numeric constraints for the plausibility of the
//instances are stated after the signature declaration.
sig Time {
	hour: one Int,
	minute: one Int
} {
	hour >= 0
	minute >= 0
}

//Similar to their counterparts for the Date signature, these two predicates (timePrecedes
//and timeFollows) are functional to other fact and assertion expressions in the
//remainder of the model.
pred timePrecedes [t1, t2 : Time] {
	t1.hour < t2.hour or (t1.hour = t2.hour and t1.minute < t2.minute)
}

pred timeFollows [t1, t2 : Time] {
	t1.hour > t2.hour or (t1.hour = t2.hour and t1.minute > t2.minute)
}

//The plausibleTime fact prevents two separate instances of Time to model the same
//time instant.
fact plausibleTime {
	no disj t1, t2 : Time |	!(timePrecedes[t1, t2] or timeFollows[t1, t2])
}

//Represents a string of characters.
sig charString {}

//Represents a geographic place in the world (e.g. an airport, a bus stop, a monument...)
sig Place {}

//--- Project-specific signature declarations ---

//The User signature is declared as abstract since it doesn't correspond to a real
//user category in the system-to-be. It aims to factor each category's shared attributes,
//so as to avoid cumbersome repetitions in the Administrator, Employee and
//Customer signatures.
abstract sig User {
	name : one charString,
	surname : one charString,
	dateOfBirth : one Date,
	homeAddress : one charString,
	email : one charString,
	username : one charString,
	password : one charString
}

sig Administrator extends User {
	createUser : set User,
	editUser : set User,
	deleteUser : set User,
}

sig Employee extends User {
	createBP : set BasicProduct,
	editBP : set BasicProduct,
	deleteBP : set BasicProduct,
	composePack : set PredefinedPackage,
	editPack : set PredefinedPackage,
	deletePack : set PredefinedPackage
}

sig Customer extends User {
	view : set PredefinedPackage,
	customize : set CustomPackage,
	buy : set Purchase
}

//As with the User signature, BasicProduct factors the common attributes shared by the
//Flight, Hotel and Excursion signatures, which have carefully been modeled after the
//prescriptions found in the class diagram.
abstract sig BasicProduct {
	ID : one Int,
	description : lone charString
}

sig Flight extends BasicProduct {
	code : one charString,
	depTime : one Time,
	depAirport : one Place,
	arrTime : one Time,
	arrAirport : one Place,
	flightFrequency : some DayOfWeek,
	price : one Int
} {
	depAirport != arrAirport
	timeFollows[arrTime, depTime]
}

sig Hotel extends BasicProduct {
	name : one charString,
	address : one Place,
	stars : one Int,
	checkinDays: some DayOfWeek,
	checkoutDays: some DayOfWeek,
	price : one Int,
	info : one charString
} {
	stars >= 1
	stars <= 5
}

sig Excursion extends BasicProduct {
	name : one charString,
	depTime : one Time,
	depPlace : one Place,
	visits : some Place,
	arrTime : one Time,
	arrPlace : one Place,
	excursionFrequency : some DayOfWeek
} {
	timeFollows[arrTime, depTime]
}

//The Package signature models a travel package, containing a (non-empty) set of basic products.
abstract sig Package {
	name : one charString,
	price : one Int,
	components : some BasicProduct
}

//Pre-defined packages can only be composed (and edited, and deleted) by employees.
sig PredefinedPackage extends Package {}

//Custom packages are the result of the customization of a pre-defined package on the part of
//a customer.
sig CustomPackage extends Package {
	basedOn : one PredefinedPackage
}

//Represent the purchase of the specified package (field "object") with the indicated date
//assignments. 
sig Purchase {
	object : one Package,
	dateAssignment : BasicProduct -> some Date
}

//--- Project-specific predicates ---

//Checks whether an administrator is the original one or not. An administrator is "original" if and
//only if he exists since the creation of the system (the default administrator, in other words), i.e.
//the only administrator who was not created by another one.
pred isOriginalAdministrator [originalCandidate : User] {
	originalCandidate in Administrator and
	no a : Administrator | originalCandidate in a.createUser 
}

//--- Project-specific functions ---

//Returns the components of a package which were assigned the specified date when purchased.
fun componentsOnDate [p : Purchase, d : Date] : BasicProduct {
	p.dateAssignment.d
}

//Dual function for componentsOnDate, returns the dates that were associated to the specified
//basic product when a package containing it was purchased.
fun datesForComponent [p : Purchase, bp : BasicProduct] : Date {
	bp.(p.dateAssignment)
}

//Prevents two distinct basic products from having the same unique attributes.
fact noDuplicatedInstances {
	//No user shares the same username and e-mail address as another one's;
	no disj u1, u2 : User | (u1.username = u2.username or u1.email = u2.email)
	//No basic product shares the same ID as another one's;
	no disj bp1, bp2 : BasicProduct | bp1.ID = bp2.ID
	//No flight shares the same flight code as another one's;
	no disj f1, f2 : Flight | f1.code = f2.code
	//No hotel is located at the same address as another one.
	no disj h1, h2 : Hotel | h1.address = h2.address
}

//Contains all regulations regarding the creation and the deletion of users.
fact userManagementRules {
	//There must be exactly one original administrator.
	one a : Administrator | isOriginalAdministrator[a]
	//All administrators (except for the original one) and employees must have been
	//created by exactly one administrator.
	all u : User |	(u in (Administrator+Employee) and !isOriginalAdministrator[u]) => 
						one a : Administrator | u in a.createUser
	//No customer is created by administrators
	all a : Administrator | no c : Customer | c in a.createUser
	//Avoids creation circles, including reflexive creation relations.
	no a : Administrator | a in a.^createUser
	//A user has been deleted by at most one dministrator.
	all u : User | #{a : Administrator | u in a.deleteUser} <= 1
	//Avoids deletion circles, including reflexive deletion relations.
	no a : Administrator | a in a.^deleteUser
}

//Contains all regulations regarding the creation and the deletio of basic products.
fact basicProductManagementRules {
	//All basic products must have been created by exactly one employee.
	all bp : BasicProduct | one e : Employee | bp in e.createBP
	//A basic product must have been deleted by at most one employee.
	all bp : BasicProduct | #{e: Employee | bp in e.deleteBP} <= 1
}

//Contains all regulations regarding the creation, the deletion and the customization of packages.
fact packageManagementRules {
	//All predefined packages must have been created by exactly one employee
	all p : PredefinedPackage | one e : Employee | p in e.composePack
	//A package must have been deleted by at most one employee
	all p : PredefinedPackage | #{e: Employee | p in e.deletePack} <= 1
	//All custom packages must have been composed by exactly one customer
	all cp : CustomPackage | one c : Customer | cp in c.customize
	//A customer must have viewed all the packages he's customized
	all c : Customer | c.customize.basedOn in c.view
}

//Contains all regulations regarding the purchase of packages and the subsequent date assignment
//that has to take place.
fact purchaseRules {
	//A purchase must be associated to one and only one customer
	all p : Purchase | #{c : Customer | p in c.buy} = 1
	//A customer cannot buy another customer's custom package
	all c : Customer | all cp : CustomPackage | cp in c.buy.object => cp in c.customize
	//A customer must have viewed all packages he's bought (no similar condition is expressed
	//for CustomPackages because the last statement in the packageManagementRules fact
	//takes care of this).
	all c : Customer | all pp : PredefinedPackage | pp in c.buy.object => pp in c.view
	//Dates must be assigned to all and only the basic products of which the package is composed of.
	all p : Purchase | all d : Date | componentsOnDate[p, d] in p.object.components
	//Exactly one date must be assigned to flights and excursions, and exactly two dates must be 
	//assigned to hotels.
	all p : Purchase | all bp : BasicProduct |
		((bp in (Flight+Excursion) => #p.dateAssignment[bp] = 1) and
		(bp in Hotel => #p.dateAssignment[bp] = 2))
	//Dates must respect the frequency constraints in each component
	all p : Purchase | all d : Date | all bp : BasicProduct |
		bp in componentsOnDate[p, d] =>
			((bp in Flight => d.weekDay in bp.flightFrequency) and
			(bp in Excursion => d.weekDay in bp.excursionFrequency) and
			(bp in Hotel => d. weekDay in (bp.checkinDays+bp.checkoutDays)))
	all p : Purchase | all h : Hotel | some disj d1, d2 : Date |	
		h in p.object.components =>
		((d1 in datesForComponent[p, h] and d1.weekDay in h.checkinDays) and
		(d1 in datesForComponent[p, h] and d2.weekDay in h.checkoutDays))
}

//--- Assertions ---

//This assertion verifies that the original administrator must have created at least another
//administrator or an employee, if any of those user types exist in the system.
assert originalAdminMustCreate {
	all a : Administrator |	(isOriginalAdministrator[a] and #(Administrator+Employee) > 1) =>
									#a.createUser > 0
}

//This assertion demonstrates that no product can be present on the system if no employee
//has ever been created. 
assert noProductExistsIfNoEmployeeExists {
	#Employee = 0 => #BasicProduct + #Package = 0
}

//This assertion shows that if no administrator exists, no system-related entity exists either
//(non-system-related entities are signatures that exist outside the system, such as basic types).
assert nothingExistsIfNoAdministratorExists {
	#Administrator = 0 => #(univ-Date-Time-DayOfWeek-charString-String-Int) = 0
}

//This assertion shows that no customer can void the components list in order to customize a
//package.
assert noCustomizationAllowsToVoidComponentList {
	no cp : CustomPackage | #cp.components = 0
}

//This assertion shows that no user can buy another customer's custom package.
assert customPackagesStayPrivate {
	no cp : CustomPackage | some disj u1, u2 : User | cp in u1.buy.object and cp in u2.customize
}

//Customers can buy only pre-defined packages, only custom ones or both kinds.
pred customersCanBuyAnything {
	some c : Customer |	#c.buy >= 0 and
									c.buy.object&PredefinedPackage != none and
									c.buy.object&CustomPackage != none
	some c : Customer | 	#c.buy >= 0 and
									c.buy.object&PredefinedPackage != none
	some c : Customer |	#c.buy >= 0 and
									c.buy.object&CustomPackage != none
}

//Customers can also view any pre-defined package they want without buying any.
pred viewWithNoPurchase {
	all c : Customer | c.view != none and c.buy = none
}

pred show {}

check originalAdminMustCreate
check noProductExistsIfNoEmployeeExists
check nothingExistsIfNoAdministratorExists
check noCustomizationAllowsToVoidComponentList
check customPackagesStayPrivate
run customersCanBuyAnything for 10
run viewWithNoPurchase for 10
run show for 5
