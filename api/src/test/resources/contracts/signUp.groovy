org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'POST'
        url '/sign-up'
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
        status OK()
        body([
            id       : 1,
            firstName: "John",
            lastName : "Smith",
            email    : "john@example.com"
        ])
        headers {
            contentType('application/json')
        }
    }
}
