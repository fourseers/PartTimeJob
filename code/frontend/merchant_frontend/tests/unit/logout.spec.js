import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'

import AdminLogin from '@/views/AdminLogin.vue'
import Logout from '@/views/Logout.vue'
import token from '@/util/token.js'

const goodresponse = {
    "data": {
        "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
        "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
        "scope": "merchant",
        "token_type": "bearer",
        "expires_in": 41138,
    }, "status": 200, "message": "success"
}

describe('logout.vue', ( ) => {
    it('test admin logout', done => {

        token.savetoken(goodresponse.data) 
        const wrapper = shallowMount(Logout);
        const vm = wrapper.vm
        vm.logout ();
        expect(token.loadToken()).toEqual( {"access_token": null, "expires_in": null, "refresh_token": null, "scope": null, "token_type": null} );

        done();
    });


})

describe('logout.vue', ( ) => {
    it('test admin  logout', done => {
        token.savetoken(goodresponse.data)

        const wrapper = shallowMount(Logout);
        const vm = wrapper.vm

        vm.$root.admin =true;
        vm.logout ();
        expect(token.loadToken()).toEqual( {"access_token": null, "expires_in": null, "refresh_token": null, "scope": null, "token_type": null} );

        done();
    });


})
