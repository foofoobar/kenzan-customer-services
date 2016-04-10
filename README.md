# kenzan-customer-services
Services for customer CRUD operations

# Steps to setup the project in Eclipse
1. git clone https://github.com/foofoobar/kenzan-customer-services.git 
2. Open Eclipse.  File > Import > General Project > Existing projects into workspace. Select the path to the cloned project
3. Right click on the project and Run As > Maven Build
4. Set up Server. Window > Open View > Servers > Add a new Server setup for Tomcat.
5. Double click on the new server to open server properties and make sure the host name is localhost and the 
configuration path is set up as /Servers/Tomcat v8.0 Server at localhost-config
6. Publish and start the server

# Sample Rest calls

#### Get a list of all customers -
```
http://localhost:8080/KenzanCustomerProject/api/CustomerService/customers (GET)
```

#### Add a new customer - 
```
http://localhost:8080/KenzanCustomerProject/api/CustomerService/customers/add (PUT)
```
```json
Sample JSON Payload -
{
    "name":"John Doe",
    "telephone":"123-345-4567",
    "email":"John.doe@gmail.com",
    "address":{"state": "MA", "city": "Boston", "zip": "111111", "street": "Main"}
}
```

##### Update a customer - 
```
http://localhost:8080/KenzanCustomerProject/api/CustomerService/customers/update (POST)
```
```json
Sample JSON Payload -
{
    "id" : 1,
    "name":"John Doe",
    "telephone":"203-345-1111",
    "email":"John.doe@gmail.com",
    "address":{"state": "CT", "city": "Stamford", "zip": "06902", "street": "Main"}
}
```

##### Delete a customer - 
```
http://localhost:8080/KenzanCustomerProject/api/CustomerService/customers/delete (POST)
```
```json
Sample JSON Payload -
{
    "id" : 1,
    "name":"John Doe",
    "telephone":"123-345-4567",
    "email":"John.doe@gmail.com",
    "address":{"state": "MA", "city": "Boston", "zip": "111111", "street": "Main"}
}
```