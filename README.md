Code Challenge Number26
=======================

Candidate: Steven Krauß
-----------------------

#About

I've used the following project structure: 

  * source
  * source / pom.xml - *(parent pom)*
  * source / server
  * source / server / pom.xml
  * README.md

You can build the project with maven *./source >* `mvn clean install` and run the project with maven using *./source/server >* `mvn spring-boot:run` 

#Adjustments

In the definition of the code challenge I was asked to respond with `{"status": "ok"}` when creating a resource via PUT resource. This is uncommon, as `"ok"` is associated to status-code 200 (OK) and RFC2616 forces us to respond with 201 (Created). So I beg your pardon for changing it to a RFC2616 conform response including the newly created entity instead of `"status"`.

Then, some words about the resource access points. I was asked to use the following spec:

  * `[PUT] /transactionservice/transaction/{id}` 
  * `[GET] /transactionservice/transaction/{id}`
  * `[GET] /transactionservice/types/{type}`
  * `[GET] /transactionservice/sum/{id}`

and I beg your pardon for changing it again to:

  * `[PUT] /transactions/{id}`
  * `[GET] /transactions/{id}`
  * `[GET] /transactions?type={type}` 
  * `[GET] /transactions/sum/{id}`
  
Explanation:
  
  * The use of `/transactionservice/transaction/` is redundant and therefore bad practice.
  * The force of the non-redundant expression `/transactions` is still the same.
  * The original `../types/{type}` is meant as a filter on the transaction-service and usually implemented with query params.
  * The adjusted `..?type={type}` should return an array of Transactions, but is still following the spec with returning and array of long. (would need any kind of element selection/filter here)
  * The original `../sum/{id}` is meant as a sub-resource resp. function of the transaction-service, therefore it's still a sub-resource.

#Asymptotic Behaviour

I must admit that I'm not familiar with Big-O resp. asymptotic behaviour, but will give it a try:

The summing is done recursive with static `TRANSACTION_SUMMING` function, where we add the parent amount to the amount calculated from children using a recursive stream. The function gets called `n` times (for each Transaction). If we would suppose the JVM does not produce any overhead, the behaviour should be O(n), but more likely it is O(nlogn). Not including, that the JIT compiler may optimize the code on the fly. 

Depending on the average amount of Transactions in a tree, the frequency of access and of course depending on the repository used to store the Transactions, it could be better to calculate and store the final amount of each *node* separately. Thus, would result in a behaviour of O(1). But as always, it depends.


