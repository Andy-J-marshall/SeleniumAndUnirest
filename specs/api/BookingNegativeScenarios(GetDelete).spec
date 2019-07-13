# Booking - GET and DELETE Negative Scenarios - API

   |BookingId |FailureReason           |
   |----------|------------------------|
   |9999999999|BookingId does not exist|
   |abc123    |Contains letters        |

## Attempting to DELETE a booking returns an error when using an invalid bookingId
* GET invalid booking using bookingId <BookingId>
* The status code should be "404"
* The response contains an error message "Not Found"

## Attempting to GET a booking returns an error when using an invalid bookingId
* DELETE a booking using bookingId <BookingId>
* The status code should be "405"
* The response contains an error message "Method Not Allowed"

## Attempting to DELETE a booking returns an error when using invalid Auth header
* DELETE a booking using no auth header
* The status code should be "403"
* The response contains an error message "Forbidden"

