import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import Logout from '@/views/Logout.vue'
import token from '@/util/token.js'


describe('getJobs.vue', () => {
    beforeAll(() => {

        const wrapper = shallowMount(Login);
        const vm = wrapper.vm
        const goodresponse = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": 41138,
            }, "status": 200, "message": "success"
        }
        vm.login_process("user_one", "user_one").then(response => {
            expect(token.loadToken().scope).toEqual(goodresponse.data.scope);

        });
    });


    it('test logout', done => {

         ("user_one", "user_one").then(response => {
            expect(response).toEqual( );

            done();
        });
    });

})
