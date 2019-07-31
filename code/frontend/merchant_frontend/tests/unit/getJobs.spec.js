import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import token from '@/util/token.js'

import {getJobs, getJobsByShop} from '@/util/getJobs.js'

import ShowJobs from "../../src/views/ShowJobs";

describe('getJobs.js', () => {

    it('tests getjob error ', async () => {
        const wrapper2 = shallowMount(ShowJobs)
        const vm2 = wrapper2.vm

        await    expect(vm2.mockTableData1(0)).rejects.toEqual(
            401);
    });
})
describe('getJobs.js', () => {

    it('tests getjob by shop error ', async () => {
        const wrapper2 = shallowMount(ShowJobs)
        const vm2 = wrapper2.vm

        await    expect(vm2.get_job_by_shop(0,1)).rejects.toEqual(
            401);
    });
})


describe('getJobs.js', () => {
    it('test getJobs.js', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            // expect(token.loadToken().scope).toEqual(goodresponse.data.scope);
            getJobs(0).then(res  => {
                expect(res.data.content[0].need_gender ).toEqual( 2);
                done();
            })
            done();
        });

    });
})
describe('getJobs.js', () => {
    it('test getJobs.js', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            const wrapper2 = shallowMount(ShowJobs)
            const vm2 = wrapper2.vm
             vm2.mockTableData1(0).then(res  => {
                expect(res.data.content[0].need_gender ).toEqual( 2);
                done();
            })
            done();
        });
    });
})

describe('getJobs.js', () => {
    it('test getJobs  BY shop', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            // expect(token.loadToken().scope).toEqual(goodresponse.data.scope);
            getJobsByShop(0,2).then(res  => {
                expect(res.data.content[0].need_gender ).toEqual( 2);
                done();
            })
            done();
        });

    });
})


describe('getJobs.js', () => {
    it('test getJobs by shop', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            const wrapper2 = shallowMount(ShowJobs)
            const vm2 = wrapper2.vm
            vm2.get_job_by_shop(0,2).then(res  => {
                expect(res.data.content[0].need_gender ).toEqual( 2);
                done();
            })
            done();
        });
    });
})
