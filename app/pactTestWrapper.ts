import { provider } from './pactSetup';

beforeAll(done => {
  provider.setup().then(() => done())
});

afterAll(done => {
  provider.finalize().then(() => done())
});
