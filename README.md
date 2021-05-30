You can run the application by choosing one of the two profile options: prod or dev. (dev includes h2 database.)

After running the application, you can check the localhost:8080/swagger-ui.html for the api's.

#### Last changes: Search added.
GET localhost:8080/search?query=u

#### Example response:
```
{
  "status": "Success!",
  "data": [
    {
      "title": "user1",
      "type": "User",
      "path": "user/2"
    },
    {
      "title": "user2",
      "type": "User",
      "path": "user/3"
    },
    {
      "title": "ethereum",
      "type": "Title",
      "path": "title/1"
    }
  ]
}
```
