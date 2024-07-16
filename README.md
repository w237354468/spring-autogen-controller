# spring-autogen-controller

> A convenient plugin which could help you generate controllers(Spring) by configuring instead of writing code in IDE. (Basic GET/POST/PUT/DELETE)

# RoadMap
## phrase 1: 
* DataModel that marks virtual tables as a foundation
* Automatic loading controller, switching datasources
* Page Query API    without writting code: support (not)equals/(not)like/between/lt(eq)/gt(eq)/in page query on at most three tables
* Info Query API    without writting code: support query by primary id
* Update API        without writting code:
* Delete API        without writting code:
* Custom event extensions on loading APIs above (be like aspects)

## phrase 2: 
* Import Query API  without writting code: 
* Emport Query API  without writting code: 

# Data Model

> Abstract data table which combines several physical tables. When CURD operations execute on abstract table, which
> means operations actually execute on several physical tables.
