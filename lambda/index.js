const { v4: uuidv4 } = require('uuid');
var AWS = require('aws-sdk');


exports.handler = async function(event) {
  console.log("request:", JSON.stringify(event, undefined, 2));

  if (event.queryStringParameters && event.queryStringParameters.targetUrl) {
    return createShortUrl(event);
  }

  if (event.pathParameters && event.pathParameters.proxy) {
    return readShortUrl(event);
  }

  return {
    statusCode: 200,
    body: 'Usage: ?targetUrl=URL'
  };
};

const createShortUrl = async (event) => {
    const tableName = process.env.TABLE_NAME;
    const targetUrl = event.queryStringParameters.targetUrl;
    const id = uuidv4().substring(0,8);

    const dbClient = new AWS.DynamoDB({
        region: 'us-west-2'
    });

    var params = {
      TableName: tableName,
      Item: {
        'id' : {
            S: id,
        },
        'target_url' : {
            S: targetUrl,
        }
      }
    };

    const url = `https://${event.requestContext.domainName}${event.requestContext.path}${id}`;

    try {
        await dbClient.putItem(params).promise();
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'text/plain'
            },
            body: `Created URL: ${url}`
        };
    } catch (err) {
        return {
            statusCode: 400,
            headers: {
                'Content-Type': 'text/plain'
            },
            body: `Error: ${err}`
        };
    }
}

const readShortUrl = async (event) => {
    const tableName = process.env.TABLE_NAME;
    const id = event.pathParameters.proxy;

    const dbClient = new AWS.DynamoDB({
        region: 'us-west-2'
    });

    var params = {
      TableName: tableName,
      Key: {
        'id': {
            S: id,
         }
      }
    }

    try {
        const response = await dbClient.getItem(params).promise();
        console.log("Response from ddb lookup: ", response.Item.target_url.S);
        return {
            statusCode: 301,
            headers: {
                'Location': response.Item.target_url.S
            }
        };
    } catch (err) {
        return {
            statusCode: 400,
            headers: {
                'Content-Type': 'text/plain'
            },
            body: `Error retrieving proxy: ${id}: ${err}`
        }
    }
}
