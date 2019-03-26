# icefire coding  challenge

Getting Started
---------------

```
# clone it
git clone https://github.com/dinesh-88/icefire-test


# build 
1. cd icefire-test
2. mvn clean install

# Run unit tests
1. mvn test

# Run Application
1. mvn spring-boot:run
```

## API

1. Login  
    ```
    URL : http://localhost:8080/icefire/login/ [POST]
    Body :  {
             "name": "dinesh",
             "password": "123"
            }          
    ```
2. Encrypt  

    ```
    URL : http://localhost:8080/icefire/entry/encrypt/ [POST]
    Body :  {
             "userId": 1,
             "entry": "test"
            }          
    ```  
3. Decrypt  

    ```
    URL : http://localhost:8080/icefire/entry/decrypt/ [POST]
    Body :  {
             "userId": 1,
             "entry": "SELPmoXr7cr8us/HJBXhVQ=="
            }          
    ```      
4. Get Encrypted list by user  

    ```
    URL : http://localhost:8080/icefire/user/{id} [GET]       
    ```    
    
## UI

1 . Login Page 

      http://localhost:8080/icefire/login.html
      
      1. User Name/ Password : dinesh / 123
      2. User Name/ Password : admin / password
       
      
  