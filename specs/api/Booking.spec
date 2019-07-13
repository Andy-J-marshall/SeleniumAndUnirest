# Booking - API

These steps are always run once to ensure that at least one booking is present

* POST to the booking endpoint using valid details
* The status code should be "200"
* The POST booking response contains the correct information

## GET all bookings
* GET all bookings
* The status code should be "200"
* The GET all bookings response contains a bookingId for each booking

## GET booking response contains the correct information after creating a booking
* GET booking using Id of the recently created booking
* The status code should be "200"
* The GET booking response for the last created bookingId contains the correct information

## DELETE a booking
* DELETE a booking
* The status code should be "201"
* Assert Created appears in response body after successfully deleting a booking
* GET all bookings
* The status code should be "200"
* GET all bookings response does not contain the deleted booking

## POST using decimal numbers
* POST the booking endpoint using a decimal place in the price
* The status code should be "200"
* The POST booking response contains the correct information

___
* CLEANUP: DELETE the last created booking
