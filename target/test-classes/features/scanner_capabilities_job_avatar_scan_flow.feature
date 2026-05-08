Feature: Scanner Capabilities Job flow for AVATAR-SCAN service

  Scenario: Client makes a valid REST Scanner Capabilities Job api call on AvatarScan Service with a valid payload
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with status code 200 for Scanner Capabilities

  Scenario: Client makes a valid REST Scanner Capabilities Job api call on AvatarScan Service with incompatible version in URL
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A INCOMPATIBLE_VERSION GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with status code 404 for Scanner Capabilities

  Scenario: Client makes a valid REST Scanner Capabilities Job api call on AvatarScan Service with a invalid URL
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A INVALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with status code 404 for Scanner Capabilities

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 404, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 404
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 410 for Scanner Capabilities and internalErrorCode AVS000101

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 429, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 429
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 503 for Scanner Capabilities and internalErrorCode AVS100112

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 404 and Web Socket returns 200
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 404
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 410 for Scanner Capabilities and internalErrorCode AVS000102

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 200 and Web Socket returns 404
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 404
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 409 for Scanner Capabilities and internalErrorCode AVS100002

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 401, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 401
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 500 for Scanner Capabilities and internalErrorCode AVS000201

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 400, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 400
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 400 for Scanner Capabilities and internalErrorCode AVS000003

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 500, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 500
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 500 for Scanner Capabilities and internalErrorCode AVS000205

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 503, Tunnel Manager 200 and Web Socket returns 200
    And CMS for set signal returns status 503
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 503 for Scanner Capabilities and internalErrorCode AVS100101

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 400 and Web Socket returns 200
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 400
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 400 for Scanner Capabilities and internalErrorCode AVS000004

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 500 and Web Socket returns 200
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 500
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 500 for Scanner Capabilities and internalErrorCode AVS000203

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 503 and Web Socket returns 200
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 503
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 503 for Scanner Capabilities and internalErrorCode AVS100103

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 200 and Web Socket returns 400
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 400
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 500 for Scanner Capabilities and internalErrorCode AVS000204

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 200 and Web Socket returns 500
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 500
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 500 for Scanner Capabilities and internalErrorCode AVS000206

  Scenario: Client makes a valid REST Scanner Capabilities Job api call when Set signal API returns 202, Tunnel Manager 200 and Web Socket returns 503
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 503
    When A VALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId mockDeviceId12345 and with VALID_CONTENT
    Then Avatar Scan service responds with response code 503 for Scanner Capabilities and internalErrorCode AVS100105

  Scenario: Client makes a valid REST Scanner Capabilities Job api call on AvatarScan Service with empty printer id
    And CMS for set signal returns status 202
    And CMS returns the tunnel config for the printer returns status 200
    And Websocket tunnel returns the Capability ESCL response with status 200
    When A INVALID GET Scanner Capabilities rest call is made on AvatarScan with cloudId  and with VALID_CONTENT
    Then Avatar Scan service responds with status code 404 for Scanner Capabilities