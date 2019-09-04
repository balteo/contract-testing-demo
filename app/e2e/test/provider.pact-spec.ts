import { Verifier, VerifierOptions } from '@pact-foundation/pact';

describe("Pact Verification", () => {
  it("validates the expectations of Matching Service", () => {

    let opts: VerifierOptions = {
      provider: "MyProvider",
      logLevel: "debug",
      providerBaseUrl: "http://localhost:8080",

      requestFilter: (req, res, next) => {
        console.log(
          "Middleware invoked before provider API - injecting Authorization token"
        );
        req.headers["MY_SPECIAL_HEADER"] = "my special value";

        next()
      },

      // Fetch pacts from broker
      pactBrokerUrl: "http://localhost:8085",

      // Fetch from broker with given tags
      tags: ["prod"],

      // Specific Remote pacts (doesn't need to be a broker)
      // pactUrls: ['https://test.pact.dius.com.au/pacts/provider/Animal%20Profile%20Service/consumer/Matching%20Service/latest'],
      // Local pacts
      // pactUrls: [
      //   path.resolve(
      //     process.cwd(),
      //     "./pacts/matching_service-animal_profile_service.json"
      //   ),
      // ],

      publishVerificationResult: true,
      providerVersion: "1.0.0",
    };

    return new Verifier(opts).verifyProvider().then(output => {
      console.log("Pact Verification Complete!");
      console.log(output)
    })
  })
});
