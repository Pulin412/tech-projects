import http from 'k6/http';
import { check } from 'k6';

export function authenticate() {
    const requestBody = {
        "email": "admin@gmail.com"
    };

    const headers = {
        headers: {
            'Content-type': 'application/json',
        },
    };

    const response = http.post(__ENV.AUTH_URL, JSON.stringify(requestBody), headers);

    // Verify response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'Access token generated': (r) => r.json().token != null,
    });

    return {
        access_token: response.json().token,
    };
}
