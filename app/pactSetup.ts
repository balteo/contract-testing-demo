import * as path from 'path';
import { Pact } from '@pact-foundation/pact';

export const port = 8991;
export const provider = new Pact({
  port: port,
  cors: true,
  log: path.resolve(process.cwd(), "logs", "mockserver-integration.log"),
  dir: path.resolve(process.cwd(), "pacts"),
  spec: 2,
  pactfileWriteMode: "update",
  consumer: "MyConsumer",
  provider: "MyProvider",
});
