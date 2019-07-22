import {shallowMount,mount} from '@vue/test-utils'
import ShowJobs from '@/views/ShowJobs.vue'
import Login from '@/views/Login.vue'


import axios from 'axios';
describe('Login.vue', () => {
    it('returns 200', done => {


        const goodresponse ={"data":{"access_token":"ed7da69b-45c0-4a19-8536-ea8ccb40bacb","refresh_token":"584ede9a-3f49-49b3-9046-c321166481c6","scope":"merchant","token_type":"bearer","expires_in":41138},"status":200,"message":"success"}

        Login.methods.login("user_one", "user_one").then(response => {
            expect(response.data.scope).toEqual(goodresponse.data.scope);

            done();
        });

    })
});



describe('ShowJobs.vue', () => {
    describe('Test for  mounted', () => {
        const wrapper = shallowMount(ShowJobs);

        it('calls mockTableData1', () => {
            // 创建mock函数 
        })
    });
})

