connect to CS157a;

CREATE table if not exists P1.Customer(ID INT NOT NULL Primary Key GENERATED BY DEFAULT AS IDENTITY
										 (START WITH 100, INCREMENT BY 1, NO CACHE),
										 Name varchar(15) not null, 
										 Gender char not null CHECK (Gender in ('M','F')),
										 Age int not null CHECK (Age >= 0),
									     Pin int not null CHECK (Pin >= 0));

CREATE table if not exists P1.Account(NUMBER INT NOT NULL Primary Key GENERATED ALWAYS AS IDENTITY 
										  (START WITH 1000, INCREMENT BY 1, NO CACHE),
										  ID int not null,
										  Balance int not null CHECK (Balance >= 0),
										  Type char not null CHECK (Type in ('C', 'S')),
										  Status char not null CHECK (Status in ('A','I')), 
										  constraint my_constraint foreign key(ID)
										  REFERENCES P1.Customer(ID) ON DELETE CASCADE);