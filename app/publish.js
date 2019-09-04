let publisher = require("@pact-foundation/pact-node");
let path = require("path");

let opts = {
  providerBaseUrl: "http://localhost:8080",
  pactFilesOrDirs: [path.resolve(process.cwd(), "pacts")],
  pactBroker: "http://localhost:8085",
  pactBrokerUsername: process.env.PACT_USERNAME,
  pactBrokerPassword: process.env.PACT_PASSWORD,
  // FIXME: clarify & fix consumer & producer versions
  consumerVersion: "2.0.0",
  publishVerificationResult: true,
  providerVersion: "1.0.0"
};

publisher.publishPacts(opts).then(() => done());
