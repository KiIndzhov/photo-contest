# Photo Contest

Team Project Assignment

Trello board - https://trello.com/b/SSq3WrQU/telerik-final-project


## Overview

A team of aspiring photographers want an application that can allow them to easily manage
online photo contests. The application has two main parts:

- Organizational – here, the application owners can organize photo contests.
- For photo junkies – everyone is welcome to register and to participate in contests. Junkies
    with certain ranking can be invited to be jury.

### Public Part

```
The public part of your projects should be visible without authentication.
```
- Landing Page (must) – you can show the latest winning photos or something else that
    might be compelling for people to register.
- Login form (must) – redirects to the private area of the application
    o Requires username and password.
- Register form (must) – registers someone as a Photo Junkie.
    o Requires username, first name, last name, and password.

### Private Part

- Dashboard page (must) **–** it is different for Organizers and Photo Junkies
    - For Organizers:
       - There must be a way to setup a new Contest.
       - There must be a way to view Contests which are in Phase I.
       - There must be a way to view Contests which are in Phase II.
       - There must be a way to view Contests which are Finished.
       - There must be a way to view Photo Junkies.
          - (If scoring is implemented) ordered by ranking (should)
    - For Photo Junkies:
       - There must be a way to view active Open contests.
       - There must be a way to view contests that the junkie currently participates in.
       - There should be a way to view finished contests that the junkie participated in.
       - (If scoring is implemented) Display current points and ranking and how much
          until next ranking at a visible place (should)
- Contest page (must)
    - The Contest Category is always visible.
    - Phase I (must)
       - Remaining time until Phase II should be displayed.
       - Jury can view submitted photos but cannot rate them yet.
       - Junkies see enroll button if the contest is Open and they are not participating.
       - If they are participating and have not uploaded a photo, they see a form for
          upload:
             - Title – short text (required)
             - Story – long text, which tells the captivating story of the phot (required)
             - Photo – file (required)
       - Only one photo can be uploaded per participant. The photo, title, and story cannot
          be edited (must). Display a warning on submit that any data cannot be changed
          later (should)

    - Phase II (must)
      - Remaining time until Finish phase.
      - Participants cannot upload anymore.
      - Jury sees a form for each submitted photo.
        - Score (1-10) (required)
        - Comment (long text) (required)
        - Checkbox to mark that the photo does not fit the contest category. If the
    checkbox is selected, score 0 is assigned automatically and a Comment
    that the category is wrong. This is the only way to assign Score outside the
    [1, 10] interval.
        - Each juror can give one review per photo, if a photo is not reviewed, a
    default score of 3 is awarded.
    - Finished
        - Jury can no longer review photos. (must)
        - Participants view their score and comments. (must)
        - In this phase, participants can also view the photos submitted by other users,
                along with their scores and comments by the Jury. (should)
- Create Contest Form (must) – either a new page, or on the organizer’s dashboard. The
following must be easy to setup.
    - Title - text field (required and unique)
    - Category – select (required)
    - Open (must) or Invitational (should) Contest. Open means that everyone (except the
        jury can join)
        - If invitational – a list of users should be available, along with the option to select
                them (should)
    - Time limit (Phase I) (must) – anything from one day to one month
    - Time limit (Phase II) (must) – anything from one hour to one day
    - Select Jury – all users with Organizer role are automatically selected (must)
        - (If scoring is implemented) Additionally, users with ranking Photo Master can also
be selected, if the organizers decide (should)
    - Cover photo (could) – a photo can be selected for a contest.
        - Option 1 **–** upload a cover photo.
        - Option 2 – paste the URL of an existing photo.
        - Option 3 – select the cover from previously uploaded photos.
        - The organizer must be able to choose between all three options and select the
easiest for him/her (all 3 required if cover photo is implemented)

### Scoring (should)

Contest participation should award points. Points are accumulative, so being invited and
subsequently winning will award 53 points totals.

- Joining open contest – 1 point
- Being invited by organizer – 3 points
- 3rd place – 20 points (10 points if shared 3rd)
- 2nd place – 35 points (25 points if shared 2nd)
- 1st place – 50 points (40 points if shared 1st)
- Finishing at 1st place with double the score of the 2nd (e.g., 1st has been awarded 8.
    points average, and 2nd is 4.3 or less) – 75 points


- In case of a tie, positions are shared, so there can be more than one participant at 1st, 2nd,
    and 3rd places, all in the same contest.

For example, two 1st places, one 2nd and four 3rds; the two winners will each get 40 points, the
only 2nd place will get the full 35 points, and the four 3rd finishers will get 10 points.

#### Ranking:

- (0-50) points – Junkie
- (51 – 150) points – Enthusiast
- (151 – 1000) points – Master (can now be invited as jury)
- (1001 – infinity) points – Wise and Benevolent Photo Dictator (can still be jury)

### Social Sharing (could)

    Participants that finish 1st, 2nd, or 3rd could have to option to share their achievement to a social media (See Appendix).

## Use cases

### New Photo Junkie

A friend of yours sent you link to the website. You see that there is an ongoing contest about
cat pics. You decide to participate but the site forces you to register first. After registering
successfully, you have the option to submit a photo to the contest.

### Being a jury

A user accumulated enough points to qualify as a jury. The user is assigned as a jury to a
newly created contest. While the contest is still in Phase I, the juror browses through the photos, but
cannot rate them yet. As soon as the contest is switched to Phase II the juror can rate the submitted
photos.

## Validation

```
Some of the validation rules include, but are not limited to:
```
### Users:

- First & Last names – no less than 2 characters, no more than 20
- Username – unique in the system
- Password – at least 8 characters

### Contests:

- Title – unique, between 5 and 50 symbols
- Category – one of predefined set of value (e.g., Animals, Nature, etc.)
- Time limits – must be in the future

## Deliverables


Provide a link to a GitLab repository that must have the following information in the
README.md file:

- Images of the database relations.
- Images of the project (screenshots).
- Link to the hosted project (if hosted online).
- Documentation describing how to build and run the project.
- Documentation how to create and fill the database with data.
- Documentation of how to use the main features of the project.

## Technical requirements

### General:

- Use Trello for project management.
- Use Git for source control management.
- Follow the good practices about writing Git commit messages.
- Implement Swagger to document you API.
- Create user documentation / manual of the application.

### Database:

- Use MariaDB as the database engine.
- Make sure the relations in the database are normalized.

### Back-end:

- Use Hibernate to access your database.
- Follow OOP’s best practices and the SOLID principles.
- Implement a security mechanism for managing users and roles.
- Provide a REST API to access the data and functionality of the project.
- Follow the best practices when designing a REST API (see Appendix).
- The "business" functionality should be tested properly (at least 80% code coverage).
- Apply error handling and data validation to avoid crashes when invalid data is entered.

### Front-End:

- For the UI you must use Spring MVC with Thymeleaf as the template engine.
- Apply error handling and data validation to avoid crashes when invalid data is entered.
- You may use Bootstrap or another CSS framework.
- You may use a free template.

## Optional Requirements

- Use a branching strategy when working with Git.
- Implement pagination to handle displaying large amounts of data.
- Integrate your app with a Continuous Integration server (e.g., GitLab’s own) and have your
    unit tests to run on each commit to the master branch.
- Host your application’s backend in a public hosting provider of your choice (e.g., Heroku).
- Host your application’s database server in a public hosting provider of your choice.


## Expectations

You must understand the system you have created. It is OK if your application has flaws or
is missing a couple of must's. What's not OK is if you do not know what is working and what is not.
That said, you should be aware of any defects or incomplete functionality and must properly
document and secure them.

Commits in the repository should give a good overview of how the project was developed,
which features were created first and the people who contributed. Contributions from all team
members must be evident through the git commit history. The repository must contain the complete
application source code and any scripts (database scripts, for example).

```
Some things you need to be able to explain during your project defense:
```
- What are the most important things you have learned while working on this project?
- What are the worst "hacks" in the project, or where do you think it needs improvement?
- What more would you do if you had another week to work on the system?
- What would you do differently if you were implementing the system again?

## Appendix

- Guidelines for Designing a Good REST API
- How to Write a Git Commit Message
- Facebook Social Sharing

## Legend

- must – The requirements marked as must are vital for the project. Implement them first.
- should – It would be nice if the requirements marked as should are implemented but they are
    not top priority.
- could – If, and only if, you finish early and have time to spare, you can implement the
    requirements marked as could. They really are optional.


