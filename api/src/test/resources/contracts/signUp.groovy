org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'POST'
        url '/api/sign-up'
        body([
            firstName  : "John",
            lastName   : "Smith",
            email      : "john@example.com"
        ])
        headers {
            contentType('application/json')
        }
    }
    response {
        status CREATED()
        body([
            id       : 3,
            firstName: "John",
            lastName : "Smith",
            email    : "john@example.com"
        ])
        headers {
            contentType('application/json')
        }
    }
}
