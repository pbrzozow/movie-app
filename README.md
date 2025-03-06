# Movie Collection App

## Overview
The Movie Collection App is a web-based platform that allows users to browse, search, and filter movies with detailed views. Registered users can engage by commenting, rating, and marking movies as watched. An admin role is available for content management. Movie data is dynamically fetched from an external API to keep the collection updated.

## Key Features
- **Movie Browsing**: Users can view a list of movies with details such as title, rating, and release year.
- **Search and Filter**: Search for movies by name and filter them by category.
- **Detailed View**: Comprehensive movie information, including synopsis, duration, and user interactions.
- **User Registration**: New users must verify their email addresses to complete the registration.
- **User Interactions**:
    - Commenting on movies
    - Rating movies
    - Marking movies as watched
- **Scheduled Data Fetching**: Automated updates from an external movie API to ensure up-to-date content.
- **Optional Admin Role**: Administrators can manage movie content, moderate comments, and oversee user interactions.

## Technical Requirements
### **Architecture & Technologies**
- **Pattern**: Model-View-Controller (MVC)
- **Backend**: Spring Framework (Spring MVC, Spring Security, Hibernate)
- **Application Server**: Apache Tomcat
- **Database**: SQL database, deployed via Docker
- **Frontend**: Thymeleaf
- **Security**:
    - Session-based authentication
    - Secure data handling
    - Email verification for user accounts

## Data Model
### **Entities**
- **Movie Entity**: Contains movie details, including an icon, title, rating, number of times watched, release year, duration, description, and category.
- **User Entity**: Stores registration details and user roles (regular user or admin).
- **Comment Entity**: Includes the comment text, timestamp, and references to both the user and the associated movie.
- **Rating Entity**: Stores the rating value and references to the user and the movie.

## Integration
- **External Movie API**: The application fetches movie data from a third-party API at scheduled intervals.
- **Email Service**: Used for email verification and potential notifications.

## Deployment
- **Application Server**: Apache Tomcat
- **Database**: SQL database deployed using Docker for easy containerized management.
