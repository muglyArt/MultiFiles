#MultiFiles
This Project uploads the images of product and retireves it

Assumtions:
This repo uses h2 database.
2 tables product and productIamges. 
The relation is productimages is foregin key to product table by product_id


Setup/testing :
git clone the repo
Create tables in h2 database
it uses JWT auth to secure the rest api
1)user send usernaem/password in postman
2)if they are correct, then jwt toekn is sent by the server
3) attach this token along with any request in header in postman
4) the taoken is validated and if correct, success response is sent
5) if token is not correct or expired, then failed response is sent.

#uploadFiles=> Post method to upload all files of the product to folder. the folder for each product will be created and fiels are stored under that folder. The database records will be inserted in product and productiamges table.

#getListFilesOfProduct=>get all images of particular product

![jwtAuthToken](https://user-images.githubusercontent.com/94879764/143251675-a9e6af0d-f556-4743-b4ad-8395525b3ff6.PNG)
![PostfilesForProduct](https://user-images.githubusercontent.com/94879764/143251711-d4d302a5-9fed-427f-832a-19afa3be31eb.PNG)
![GetFilesByProductNames](https://user-images.githubusercontent.com/94879764/143251720-5eb00cb1-02ad-4f48-9d20-146d124a4ecc.PNG)
![unAuthorized](https://user-images.githubusercontent.com/94879764/143251734-0daa9e90-d15c-48ea-87b3-6e5ef32f5258.PNG)
