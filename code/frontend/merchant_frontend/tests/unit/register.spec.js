import {shallowMount,mount} from '@vue/test-utils'
import Register from '@/views/Register.vue'
import token from '@/util/token.js'
describe('Register.vue', () => {
    it('register() returns response', done => {

    const goodresponse = {
        "data": {
            "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
            "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
            "scope": "merchant",
            "token_type": "bearer",
            "expires_in": 41138,
        }, "status": 200, "message": "success"
    }
        Register.methods.register("user_one", "user_one").then(response => {
        expect(response.data.scope).toEqual(goodresponse.data.scope);
        done();
    });

})



})
