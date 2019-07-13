# Booking - POST Negative Scenarios - API

   |checkInDate|checkOutDate|firstName|lastName|totalPrice|depositPaid|
   |-----------|------------|---------|--------|----------|-----------|
   |[null]     |[auto]      |[auto    |[auto]  |[auto]    |[auto]     |
   |[auto]     |[null]      |[auto    |[auto]  |[auto]    |[auto]     |
   |[auto]     |[auto]      |[null]   |[auto]  |[auto]    |[auto]     |
   |[auto]     |[auto]      |[auto    |[null]  |[auto]    |[auto]     |
   |[auto]     |[auto]      |[auto    |[auto]  |[null]    |[auto]     |
   |[auto]     |[auto]      |[auto]   |[auto]  |[auto]    |[null]     |
   |           |[auto]      |[auto    |[auto]  |[auto]    |[auto]     |
   |[auto]     |            |[auto    |[auto]  |[auto]    |[auto]     |
   |[auto]     |[auto]      |         |[auto]  |[auto]    |[auto]     |
   |[auto]     |[auto]      |[auto    |        |[auto]    |[auto]     |
   |[auto]     |[auto]      |[auto    |[auto]  |          |[auto]     |
   |[auto]     |[auto]      |[auto]   |[auto]  |[auto]    |           |

## Attempting to POST a booking returns an error when using null or empty fields
tags: failing
* POST to the booking endpoint using either null, empty or auto generated values for checkInDate <checkInDate>, checkOutDate <checkOutDate>, firstName <firstName>, lastName <lastName>, totalPrice <totalPrice>, depositPaid <depositPaid>
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using a checkout date earlier than the checkin date
tags: failing
* POST the booking endpoint using checkInDate "2019-12-30", checkOutDate "2019-07-30" and auto generated data
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using an invalid date format
* POST the booking endpoint using checkInDate "30-01-2019", checkOutDate "30-02-2019" and auto generated data
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using an invalid date
* POST the booking endpoint using checkInDate "31-13-2019", checkOutDate "31-13-2019" and auto generated data
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when only using numberic characters in the name fields
tags: failing
* POST the booking endpoint using firstName "123", lastName "456" and auto generated data
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when onlu using special characters in the name fields
tags: failing
* POST the booking endpoint using firstName "!%^&*(()£?><\|@:~", lastName "|\!%^&*(()£?><@:~" and auto generated data
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using a negative number for the price
tags: failing
* POST the booking endpoint using "-1" for the price
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using an excessive number for the price
tags: failing
* POST the booking endpoint using "2147483648" for the price
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using letters for the price
* POST the booking endpoint using "1abc" for the price
* The status code should be "500"
* The response contains an error message "Internal Server Error"

## Attempting to POST a booking returns an error when using a non-boolean value for the deposit paid field
tags: failing
* POST the booking endpoint using a non-boolean value for the deposit paid field
* The status code should be "500"
* The response contains an error message "Internal Server Error"
