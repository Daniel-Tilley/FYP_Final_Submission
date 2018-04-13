export class Request {
  base_url: string;
  url_extension: string;
  headers: {
    tokenString: string;
    basicAuth: {
      username: string;
      password: string;
    }
  };
  body: object;

  constructor () { }
}
