```                                                                                                                                    
                                              dddddddd                                                                                                
kkkkkkkk                                      d::::::d                                                   lllllll                   kkkkkkkk           
k::::::k                                      d::::::d                                                   l:::::l                   k::::::k           
k::::::k                                      d::::::d                                                   l:::::l                   k::::::k           
k::::::k                                      d:::::d                                                    l:::::l                   k::::::k           
 k:::::k    kkkkkkk ooooooooooo       ddddddddd:::::d     ssssssssss      ooooooooooo   zzzzzzzzzzzzzzzzz l::::l uuuuuu    uuuuuu   k:::::k    kkkkkkk
 k:::::k   k:::::koo:::::::::::oo   dd::::::::::::::d   ss::::::::::s   oo:::::::::::oo z:::::::::::::::z l::::l u::::u    u::::u   k:::::k   k:::::k 
 k:::::k  k:::::ko:::::::::::::::o d::::::::::::::::d ss:::::::::::::s o:::::::::::::::oz::::::::::::::z  l::::l u::::u    u::::u   k:::::k  k:::::k  
 k:::::k k:::::k o:::::ooooo:::::od:::::::ddddd:::::d s::::::ssss:::::so:::::ooooo:::::ozzzzzzzz::::::z   l::::l u::::u    u::::u   k:::::k k:::::k   
 k::::::k:::::k  o::::o     o::::od::::::d    d:::::d  s:::::s  ssssss o::::o     o::::o      z::::::z    l::::l u::::u    u::::u   k::::::k:::::k    
 k:::::::::::k   o::::o     o::::od:::::d     d:::::d    s::::::s      o::::o     o::::o     z::::::z     l::::l u::::u    u::::u   k:::::::::::k     
 k:::::::::::k   o::::o     o::::od:::::d     d:::::d       s::::::s   o::::o     o::::o    z::::::z      l::::l u::::u    u::::u   k:::::::::::k     
 k::::::k:::::k  o::::o     o::::od:::::d     d:::::d ssssss   s:::::s o::::o     o::::o   z::::::z       l::::l u:::::uuuu:::::u   k::::::k:::::k    
k::::::k k:::::k o:::::ooooo:::::od::::::ddddd::::::dds:::::ssss::::::so:::::ooooo:::::o  z::::::zzzzzzzzl::::::lu:::::::::::::::uuk::::::k k:::::k   
k::::::k  k:::::ko:::::::::::::::o d:::::::::::::::::ds::::::::::::::s o:::::::::::::::o z::::::::::::::zl::::::l u:::::::::::::::uk::::::k  k:::::k  
k::::::k   k:::::koo:::::::::::oo   d:::::::::ddd::::d s:::::::::::ss   oo:::::::::::oo z:::::::::::::::zl::::::l  uu::::::::uu:::uk::::::k   k:::::k 
kkkkkkkk    kkkkkkk ooooooooooo      ddddddddd   ddddd  sssssssssss       ooooooooooo   zzzzzzzzzzzzzzzzzllllllll    uuuuuuuu  uuuukkkkkkkk    kkkkkkk
```

### 01.01.2021 UPDATE

The file which name is 'application-prod.properties' has been ignored now. If you want to use the prod profile, create
your own 'application-prod.properties' file.

#### application-prod.properties:

```  
# ------------------------------------------------------- #
# This configuration is for the production environment.   #
# ------------------------------------------------------- #
# ------------------------------
# Connector
# ------------------------------
spring.datasource.url=jdbc:YOUR_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
# ------------------------------
# Spring JPA
# ------------------------------
spring.jpa.hibernate.ddl-auto=none
```

# kod-sozluk

kod-sozluk is a server-side clone of eksisozluk.com.

#### What can it do for you?

- Fully authentication and authorization.
- Unique topics
- Entries for the topics
- Pageable access each entities
- Auditable (Who, when columns etc.)
- Adding entries to favorites.
- Intrinsically, blocks the capital letters automatically.

#### What will it do for you in the future?

- One to one messaging
- HyperText support.
- And -maybe- your suggestions. :]

After running the application, you can check the localhost:8080/swagger-ui.html for the api's. Forthermore, you can
import the Insomnia collection for APIs: <br>
-> https://github.com/berkanaslan/kod-sozluk/tree/master/src/main/resources/insomnia-collection.json

Project's SQL DDL bulk already shared the repository's WiKi page. If a change is made, that WiKi page is updated. <br>
-> https://github.com/berkanaslan/kod-sozluk/wiki/Versioned-SQL-DDLs

