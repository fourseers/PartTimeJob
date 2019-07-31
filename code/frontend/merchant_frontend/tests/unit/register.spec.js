import {shallowMount,mount} from '@vue/test-utils'
import Register from '@/views/Register.vue'
import token from '@/util/token.js'
describe('Register.vue', () => {
    it('register() returns response', done => {
        Register.methods.register("user_two", "user_two").then(response => {
        expect(response.data.scope).toEqual();
        done();
    });

})



})
