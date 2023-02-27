## Reporting service

API generating PDF reports and frontend that consumes it.

Backend stack: Java 17, Gradle 7.6 and Spring Boot 3.0.2

Frontend stack: Vue 3 and Vite

### Usage

- Make sure that port `8080` and `5173` are available and not in use
- Navigate to the root directory of the app and run `docker compose up` to get backend and frontend services running
- Open http://localhost:5173/ in the browser for testing

### Thought process

- Utilised docker and docker compose in order to create a seamless setup
- Created report and product services that are responsible for generating production report, based on some mocked
  products and request body
- Report is stored on local disk after being generated, in a production environment, it would be stored on the cloud
- Request validation and exception handling are in place
- Prolonged timeout duration from 60s to 120s for handling long running jobs, was thinking about pushing the report
  generation job to a queue/worker,
  but it doesn't fit in the current use case as the client would be waiting for a response as HTTP requests can only
  flow in one direction from the client side, two-way communication solution could be using web sockets for handling
  long running jobs, but this would be more suitable with a frontend that displays dashboards that need to be updated
  instantly for example not for downloading a report, looking forward to discuss
  this point
- Added tests for success and failure scenarios
- Created frontend app with a form and a generate button for instant download on the client side

### Improvements

- Adding more tests
- Storing the file on a blob storage solution such as AWS S3 instead of storing on the local disk
- Persisting file name and path to database