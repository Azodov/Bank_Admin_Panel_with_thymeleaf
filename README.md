
# E-Bank v1.0




## API Reference

#### Login with OAuth v2.0

```http
  GET localhost:8080
```

#### Get Client List

```http
  GET /list
```

#### Get transactions history

```http
  GET /history
```

#### Make order transfer

```http
  GET /transfer
```
#### Register new Client

```http
  GET /addEmployeeForm
```

#### Show OAuth v2.0 objects after Login

```http
  GET /auth
```

#### Update Client info

```http
  GET /showUpdateForm
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `employeeId`      | `int` | **Required**. Id of item to fetch |


#### Logout

```http
  GET /logout
```
## Authors

- [Donik11](https://www.github.com/donik11)
## Features

- Register new clinet and auto generate cards (Uzcard, Humo, Visa)
- Ability to perform transactions between cards
- Record and view transaction history
- Ability to delete, update, create users
- Find user with any type (Card Number, Full name, Type, Balance, Status, Given time and many more)
- All data saved secured postgresql database
## Installation

Install E-Bank project with cmd

```bash
  git clone https://github.com/Donik11/Bank_Admin_Panel_with_thymeleaf.git

  cd Bank_Admin_Panel_with_thymeleaf

  java -jar e-bank.jar
```
    
## Screenshots

![Login with OAuth v2.0](https://i.ibb.co/fGx3xDJ/image.png)
![List of Clients (index page)](https://i.ibb.co/ScNRt8c/image.png)
![Add new Client](https://i.ibb.co/M8SnRf7/image.png)
![History](https://i.ibb.co/PhfjjNg/image.png)
![Make order transactions](https://i.ibb.co/rfMWCVn/image.png)
## Support

For support, email botirxonn@gmail.com
## Roadmap

- Additional browser support

- Add more integrations

