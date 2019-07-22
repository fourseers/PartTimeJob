import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import token from '@/util/token.js'
describe('Login.vue', () => {
    describe('Test for  Button Component', () => {
        const wrapper = shallowMount(Login);

        it('calls handleSubmit(\'formInline\') when click on button', () => {
            // 创建mock函数
            const mockFn = jest.fn();
            // 设置 Wrapper vm 的方法并强制更新。
            wrapper.setMethods({
                handleSubmit: mockFn
            });
            // 触发按钮的点击事件
            wrapper.find('button').trigger('click');
            expect(mockFn).toBeCalled();
            expect(mockFn).toHaveBeenCalledTimes(1)
        })
    });
})

describe('Login.vue', () => {
    it("Dom correct  ", async () => {
        const wrapper = mount(Login);

        const form = wrapper.find({ ref:"formInline" }) ;
        expect(form.is('Form')).toBe(true);
        const user = wrapper.find( "#name") ;
        expect(user.is('Input')).toBe(true);
        const password = wrapper.find( "#password") ;
        expect(password.is('Input')).toBe(true);
    })
})


import axios from 'axios';
describe('Login.vue', () => {
    it('login() returns response', done => {

        const goodresponse = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": 41138,
            }, "status": 200, "message": "success"
        }
        Login.methods.login("user_one", "user_one").then(response => {
            expect(response.data.scope).toEqual(goodresponse.data.scope);

            done();
        });

    })
});


describe('Login.vue', () => {
    it('test savetoken ok ', done => {
        const wrapper = shallowMount(Login)
        const goodresponse = {
            "data": {
                "access_token": "ed7da69b-45c0-4a19-8536-ea8ccb40bacb",
                "refresh_token": "584ede9a-3f49-49b3-9046-c321166481c6",
                "scope": "merchant",
                "token_type": "bearer",
                "expires_in": 41138,
            }, "status": 200, "message": "success"
        }
        const vm = wrapper.vm
        vm.login_process("user_one", "user_one").then(response => {
            expect(token.loadToken().scope).toEqual(goodresponse.data.scope);

            done();
        });
    });
})

describe('Login.vue', () => {

    const wrapper = shallowMount(Login)
    const vm = wrapper.vm
    it('tests password wrong', async () => {
       // expect.assertions(1);
        await expect(vm.login_process("user_one", "user_one222")).rejects.toEqual(
            400);
    });
})


describe('Login.vue', () => {

    const wrapper = shallowMount(Login)
    const vm = wrapper.vm
    it('tests no params ', async () => {
        // expect.assertions(1);
        await expect(vm.login_process("", "")).rejects.toEqual(
            400);
    });
})



describe('Login.vue', () => {

    const wrapper = shallowMount(Login)
    const vm = wrapper.vm
    it('tests no params ', async () => {
        // expect.assertions(1);
        await expect(vm.login_process("", "")).rejects.toEqual(
            400);
    });
})
