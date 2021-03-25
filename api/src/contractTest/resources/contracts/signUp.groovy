package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/api/user'
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
