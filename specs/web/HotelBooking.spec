# Hotel booking - Web
tags: web

## User can add a booking
* Navigate to the hotel booking page
* Do a GET to the Booking endpoint and find the current number of bookings
* Create a booking using the website
* Do a GET to the Booking endpoint and check the number of bookings has increased

## User cannot add a booking if using invalid credentials
* Navigate to the hotel booking page
* Do a GET to the Booking endpoint and find the current number of bookings
* Attempt to create a booking using the website using invalid credentials
* Do a GET to the Booking endpoint and check the number of bookings has not increased

## User can delete a booking
* POST to the booking endpoint using valid details
* The POST booking response contains the correct information
* Do a GET to the Booking endpoint and find the current number of bookings
* Navigate to the hotel booking page
* Delete a booking using the website
* Do a GET to the Booking endpoint and check the number of bookings has decreased
