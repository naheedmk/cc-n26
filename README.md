Code Challenge Number26
=======================

Candidate: Steven Krauﬂ
-----------------------

#TODO
  * Tests (at least try calling each resource with Postman^^)
  * remove boilerplate code (repository stuff)
  * improve Exception mapping and handling
  * re-think about child to parent relationship and implementation
  * then upgrade
  
##Upgrade
  * **PUT** is not made to **POST** entities - upgrade and document
  * resource paths are inconsistent should be:
  * `   [GET] /transaction/{id}`, 
  * `   [GET] /transaction?type={type}` *(sorting and paging, not just IDs)*, 
  * `   [GET] /transaction/sum/{id}`, 
  * `  [POST] /transaction`, 
  * `   [PUT] /transaction/{id}`, 
  * `   [PUT] /transaction`, 
  * `[DELETE] /transaction/{id}`, 
  * `[DELETE] /transaction` 
  * ... 