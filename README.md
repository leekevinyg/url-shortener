# URL Shortener

A simple URL shortener built using the AWS Cloud Development Kit (CDK)

## Architecture

This CDK app uses API Gateway to expose a lambda function that either:

1. Creates a short URL in a dynamodb table

2. Retrieves a shortURL from a dynamodb table

## Usage

Hitting this URL: 

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/?targetUrl=https://google.ca

will return a short URL for https://google.ca. 

For example, the URL shortener may give you back this URL:

https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/6c819f46

Hitting the above "short" url in any browser will redirect you back to https://google.ca.
