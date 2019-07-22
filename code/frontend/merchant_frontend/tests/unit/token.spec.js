
import Login from '@/views/Login.vue'
import token from '@/util/token.js'
describe('token.js', () => {
    it('test token loadToken', done => {

            var mytoken={"access_token": null, "expires_in": null, "refresh_token": null, "token_type": null,"scope":null}
            expect(token.loadToken()).toEqual(mytoken );

            done();

    })
});
describe('token.js', () => {
    it('test token savetoken', done => {
        const goodresponse = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": 41138,
            }, "status": 200, "message": "success"
        }
        const goodresponse2 = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": "41138",
            }, "status": 200, "message": "success"
        }
        token.savetoken(goodresponse.data);
        expect(token.loadToken()).toEqual( goodresponse2.data);

        done();

    })
});

describe('token.js', () => {
    it('test token deletetoken', done => {
        const goodresponse = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": 41138,
            }, "status": 200, "message": "success"
        }
        const goodresponse2 = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": "41138",
            }, "status": 200, "message": "success"
        }
        token.savetoken(goodresponse.data);
        expect(token.loadToken()).toEqual( goodresponse2.data);
        token.deleteToken();
        var mytoken={"access_token": null, "expires_in": null, "refresh_token": null, "token_type": null,"scope":null}
        expect(token.loadToken()).toEqual(mytoken);
        done();

    })
});
