#  Grocery Store API

E-Commerce Backend API  is the backend API for an online grocery store. I built this as my third capstone project using Java and Spring Boot. The project was handed to me as a partially working codebase — my job was to hunt down bugs in the existing code and then build out several new features on top of it.
All the changes I made are in the Spring Boot API. The frontend was provided as a starter and connects to my API to power the full shopping experience.
<img width="1918" height="877" alt="Screenshot 2026-06-26 023421" src="https://github.com/user-attachments/assets/2aa52293-8a48-4336-96b3-025629b1fde1" />


# What I Built

Phase 2 – Bug Fixes
This was the first thing I tackled. The existing product search was broken in two separate ways and I had to dig through the code to find both.

Phase 3 – Shopping Cart
I implemented the full shopping cart feature from scratch. The database table already existed, but all the logic and REST endpoints needed to be built.
The cart persists between sessions. If you log out and log back in, your items are still there.
<img width="1890" height="932" alt="Screenshot 2026-06-26 024029" src="https://github.com/user-attachments/assets/70977444-9821-4327-afe5-5d7529474d7b" />

Phase 4 – User Profile
When a user registers, a profile record is automatically created for them. I built the endpoints that let a logged-in user view and update that profile.
Both endpoints are protected you have to be logged in to use them.
<img width="754" height="471" alt="Screenshot 2026-06-26 023859" src="https://github.com/user-attachments/assets/f6bea38c-1423-42a6-9bf8-4962123b5fd7" />
<img width="1882" height="919" alt="Screenshot 2026-06-26 024122" src="https://github.com/user-attachments/assets/14653ccf-914d-4b0b-9ea4-cb58961c7557" />

Phase 5 – Checkout
This was the most complex feature because none of the code for it existed yet. I created the full order flow from scratch following the same layered pattern as the rest of the project.

When a user checks out:
The API grabs their current shopping cart
A new order record is inserted into the orders table
Each cart item becomes an order_line_item record
The cart is cleared after the order is created

# 1. Set Up the Database

Open MySQL Workbench and run the grocery store database script:

database/grocery-store.sql

# 2. Run the API

bashmvn spring-boot:run

# 3. Log In and Test

Use Insomnia to hit POST /login and grab the JWT token from the response. Add it as a Bearer token header on any request that requires authentication.
<img width="1900" height="987" alt="Screenshot 2026-06-26 024510" src="https://github.com/user-attachments/assets/73b8f3d0-0933-4bb3-bf88-a3ee0a68abe5" />

# 4. Interesting Piece of Code – ProfileController

The part of this project I'm most proud of is the ProfileController. It looks simple on the surface but there's a security pattern baked in that I thought was really clever once I understood it.
<img width="1311" height="869" alt="Screenshot 2026-06-26 024348" src="https://github.com/user-attachments/assets/ab574a68-75d8-46ed-8de8-b52b8ae5374f" />
<img width="1310" height="800" alt="Screenshot 2026-06-26 024429" src="https://github.com/user-attachments/assets/aa68fc78-d576-418a-b5d7-9f3b355e7c91" />






