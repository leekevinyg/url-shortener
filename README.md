# URL Shortener

A simple URL shortener built using the AWS Cloud Development Kit (CDK)

## Architecture and Usage Guide

The CDK app uses AWS API Gateway to expose an AWS lambda function that interacts with a simple dynamodb table.

The dynamodb table contains mappings from the short form of a given URL (represented as the first 8 digits of a UUID) 
to it's original form.

For example, the UUID "6c819f46" may be mapped to https:google.ca

A user can hit the application with a targetURL query parameter like this:

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/?targetUrl=https://google.ca

and receive the "short" form of the URL like this:

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/6c819f46

Hitting the above "short" URL in any browser will then redirect the user back to https://google.ca
