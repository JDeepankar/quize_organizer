1. **Assumptions**

Before starting, I made a few basic assumptions to keep the project simple and focused:

This project only needs a backend. I don’t need to build any UI.

Anyone can create or take a quiz. I’m not adding authentication or roles for now because it would make the project
heavier.

A quiz can contain multiple types of questions:

       MCQ (with one correct option)
       True/False
       Simple text answer

The goal is to make the system clean, reliable, and production-ready even if the feature set is small.

Storing quiz attempt history is optional, so for now I will just calculate results on the fly and return them in the API
response.

These assumptions helped me limit the scope and build something minimum but still useful.

2. **Scope of the Project
   What I decided to include**

API to create a quiz with:

Title

Multiple questions

MCQ options if the question type is MCQ

API to fetch a quiz so that anyone can take it.

API to submit answers and get:

Score

Total number of questions

Breakdown of correct/wrong answers

A clean project folder structure (controller → service → repository → entity → dto)

MySQL database tables for quizzes, questions, and options.

What I did NOT include (to keep it simple)

Authentication / Admin login

Quiz attempt storage in the database

Editing or deleting quizzes

Multiple correct answers for MCQ

Fancy text evaluation (like semantic similarity)

My main focus was to build something simple, correct, readable, and easy to maintain.

3. **High-Level Architecture & Approach
   Backend**

I used Spring Boot because it lets me build APIs quickly, keeps things structured, and works well with JPA for database
operations.

The project is split into:

Controller → Handles incoming API requests and gives responses.

Service → Contains the actual business logic (create quiz, evaluate answers, etc.).

Repository → Talks to the database using Spring Data JPA.

Entities → Represent DB tables.

DTOs → Represent request and response objects.

How data flows

Create Quiz

API receives JSON input.

Service converts it into entities.

Quiz, Questions, and Options are saved through JPA using cascading.

Get Quiz

Service fetches quiz + questions + options.

Returns only what the user needs (correct answers are NOT exposed).

Submit Quiz

Service loads quiz.

Compares user’s answers with correct ones.

Calculates score.

Returns a detailed result.

Database Schema

Tables:

quiz

questions

quize_options

Relationships:

One quiz → many questions

One question → many options (only for MCQ)

4. **Scope Changes During Implementation**

While implementing, I made a few adjustments:

Originally planned to store quiz attempts, but removed it to keep the project small.

Cleaned up DTO structure so that questions contain their own options instead of putting them at the quiz level.

Adjusted table names and column names to match Spring Boot mappings.

Simplified text answer evaluation to lowercase match or contains match.

These small changes made the project cleaner and easier to finish in the available time.

5. **Reflection — What I Would Do Next If I Had More Time**

If I had more time, I would extend the system in these ways:

Add user roles (Admin vs Participant)

Admin can create quizzes

Public users can only take them

Store quiz attempts

So admins can see results, analytics, or leaderboards.

Add pagination, search, and filters

For listing quizzes or past attempts.

Better evaluation for text questions

Maybe using simple NLP or even AI scoring.

Add a small frontend

So users can take quizzes easily without Postman.

Deploy to cloud (Render / Railway / AWS)

And maybe add CI/CD using GitHub Actions.

Overall, I focused on getting the core quiz flow working well, keeping the code clean, and making sure the APIs behave
reliably.