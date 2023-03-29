import requests

url = "http://localhost:9000/api/setting/1"

# basic auth
admin = "admin"
password = "passwd"
auth = (admin, password)

# make the request
response = requests.get(url, auth=auth)

# check the response status code
if response.status_code == 200:
    # process the response data
    data = response.json()
    print(data)
else:
    print("Error: API call failed with status code", response.status_code)
