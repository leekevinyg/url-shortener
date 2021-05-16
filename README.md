# URL Shortener

A simple URL shortener built using the AWS Cloud Development Kit (CDK)

## Architecture

The CDK app uses AWS API Gateway to expose an AWS lambda function that interacts with a simple dynamodb table.

The dynamodb table contains mappings from the short form of a given URL (represented as the first 8 digits of a UUID) 
back to it's original long form.

For example, the UUID "6c819f46" may be mapped to an URL like this: https://www.google.ca/search?q=aws+cdk&sxsrf=ALeKk01gM4j3GhFn9tQqnovbXfe5U9a4iA:1621146687135&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjU7aiDys3wAhWSrp4KHXOtAZoQ_AUoA3oECAEQBQ&biw=1920&bih=1432

## Usage Guide

A user can hit the application with a targetUrl query parameter like this:

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/?targetUrl=https://www.google.ca/search?q=aws+cdk&sxsrf=ALeKk01gM4j3GhFn9tQqnovbXfe5U9a4iA:1621146687135&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjU7aiDys3wAhWSrp4KHXOtAZoQ_AUoA3oECAEQBQ&biw=1920&bih=1432

to receive the short form of the URL:

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/65d44f02

Hitting the short form of the URL will then redirect the user back to the original long form.
