### Quiz Application ###

An application for preparing quiz questions.                                                                                                                                  
User will attempt the quiz and will be scored on his/her right choices.

The handler methods are handling exceptions along with returning response entities with appropriate HTTP response codes. 

IDE: IntelliJ                                                                                                                                                    
Database: PostgreSQL      
Dependencies:      
-> Spring web      
-> Spring data JPA    
-> PostgreSQL Driver    
-> Lombok      

Main Entities:    
-> Question    
-> Quiz (@ManyToMany relationship with Question)      

APIs:     
a) GET  question/all -> get list of all questions    
b) GET  question/category/{cat} -> get list of all questions by category    
c) POST  question/add -> add a question    
d) DELETE  question/{id} -> delete a question by id    
e) PUT  question -> update a question    
f) POST quiz/create?category=<category>&numQ=<numQ>&quizTitle=<quizTitle> -> create a new quiz    
g) GET  quiz/{id} -> get a quiz by id comprising of list of questions    
h) POST  submit/{id} -> submit a quiz by id and get score    
