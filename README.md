# SXMP Onboarding Project

Live [here](http://eddiejrojas.com).

SXMP-Content Viewer. This full-stack application provides a viewer access to a colossal database of podcasts and songs to listen to.

The app is accessible through a React-based client that allows users to interact with the underlying backend in several ways. Including but not limited to:

- Play content and have full control over the content's timeline.

- Register and log in to their own accounts.

- Add or remove content to their personal playlist.

- Like or dislike content and see a running tally of likes/dislikes on content.

- Create update or delete content.

- Sort content by different criteria. Such as the author or the title.

- Search against a massive 3rd party API that provides a database of podcasts and music.

![Users can also like, favorite, and downvote content](https://i.imgur.com/7GdlEER.png)

This is all done through a backend written in Java that is based on the Spring Boot framework. The backend communicates with several services that allow it to persist and validate data in a PostgreSQL database.

## Architecture

The architecture of the application closely follows a _Model View Controller_ pattern, or MVC. And, to match, our working directories are named as such.

The _View_ part of the architecture is expressed within the React frontend, where the user has complete access to our CRUD application in an abstract way through the GUI. Under the src/pages directory are all the landing pages the user will see in the browser.

![Directory structure](https://i.imgur.com/ikkfST8.png)

The Controller part of our archicture are all the RESTful routes that are exposed by our back system. They are expressed in the files named with the _Controller_ suffix under every type of object that we deal with.

Last but not least, the _Model_ part of our architecture. The models are the actual data objects themselves that we deal in. Our models are essentiall the DNA structures of our data, which our ORM, or, Object Relational Mapper, uses to map and parse the data from the PSQL database.

The most important part of an MVC design is scalability. To that end, the application is carefully designed to follow a specific pattern of information flow. For example, views communicate with controllers through the API, which, in turn, communicate with models via Services. This separation of control is essential in keeping an application organized and easy to develop.

### External API

To retrieve data, our backend consumes a 3rd party API called [Podchaser](https://features.podchaser.com/api/). We use Graphql queries that structure our data and then parse the responses into POJOs, which are Java models structured to receive that data.

When it comes to parsing 3rd party data, there are several object oriented programming principles I adhered to to keep the process clean and efficient. _Abstraction_: I delegated some parsing functionality to specific classes with private handler methods to keep the main data flow clean and function calls small. _Encapsulation_: having all relevant methods to perform a specific task in one place. _Inheritance_ our data structures extend and inherit from one another. To give an example, the `Content` class is inherited from by `UserContent`, which provides a way to use the same structure but imply that we expect additional user data from that content.

### Client Architecture

The React frontend follows a strict pattern of its own as well. Our application uses a centralized API client in Api.js that is used to make all outgoing requests. These requests are then invoked through our Redux store and nowhere else. For speed, the results are parsed and cached in our store where they are available for our entire application.

![The landing page of SXMP](https://i.imgur.com/TfJ7UP8.png)

## Technologies Used

1. React
2. Redux
3. Spring Boot
4. Node.js
5. Postgresql
6. React-Bootstrap
7. Font-Awesome
8. JWT
9. Bcrypt

##

![Every content has its own page](https://i.imgur.com/NtZ57cW.png)

## Build and Deploy

Our app is divided into three parts. The `frontend` and the `backend` and the database.

To deploy the frontend, make sure you have Node installed. In a terminal, navigate to `/frontend` and run `npm install` to install all dependencies.

To set up the database, make sure you have PostgreSQL installed. Using your Linux terminal, run `sudo apt install postgresql postgresql-contrib`.

Run `sudo service postgresql start` to get it going, and create a database named sxmp with `createdb sxmp`.

Finally, for the backend, first, make sure you have Java JDK 17 installed and configured as well as Maven.

Then make sure you have all the correct environment variables set up. You'll need to sign up for the Podchaser API to retrieve your own API_ID and secret.

Finally set up application.properties in `src/main/resources/` to have the right credentials for your PSQL login. These may vary depending on your set up.

![application.properties](https://i.imgur.com/KoMQD6o.png)

From `/backend` call `./mvnw spring-boot:run` to get your API going.

### Environment Variables

- `SXMP_JWT_SECRET`
  The Secret for our external podchaser API. Needed to authenticate and pull data from an external source.

- `SXMP_API_ID`
  The ID for our external podchaser API.

- `SXMP_API_SECRET`
  Our application's secret. A string used to sign and verify tokens.

- `SXMP_API_URL`
  Our graphql endpoint for all external API calls.

![Users can register and sign in](https://i.imgur.com/2RllBAZ.png)

### API Endpoints

`GET /api/content/{id}` Get a piece of content by ID.

`GET /api/content` Get all locally cacehd content.

`PUT /api/content/{id}` Update a piece of content. Requires auth.

`DELETE /api/content/{id}` Requires authorzation header. Delete a content.

`POST /api/auth/register` Register an user. Expects a body with the following fields:

```json
{
  "email": "",
  "password": "",
  "handle": ""
}
```

`POST /api/auth/login` Login an user, expects a JSON body with username and password. Returns a JWT bearer token for authentication. Token can be used in an `Authorization` header with the `Bearer ` prefix.

`POST /api/content/` Create content. Requires authorization. Expects a JSON body:

```json
{
  "name": "",
  "description": "",
  "source": "",
  "audio": "",
  "image": "",
  "title": ""
}
```

`PUT /api/content/{id}/like` Requires authorization. Like a content. Can also `/dislike`.

## Application Data Flow

![Diagram detailing data flow between services](https://i.imgur.com/v0QBvow.jpg)

The application has been designed with a singular goal in mind to keep information flow consistent and controlled. I delegate specific tasks to specific services or static classes so that everything is defined in one place keeping in with *separation of concerns* design principles.

Data is fetched from the client through the Redux cache which invokes the API client. From there, API routes invoke the controllers which communicate with services that use the models and the database. With such a simple system in place, extending this application would be a trivial task as long as one adheres to the design.
