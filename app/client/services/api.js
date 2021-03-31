import axios from 'axios';

export default () => {
    return axios.create({
        baseURL:'https://pricechecker.mkredzel.me/api',
        withCredentials: false,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        }
    });
}
