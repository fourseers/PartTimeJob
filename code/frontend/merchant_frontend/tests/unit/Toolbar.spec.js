import VueTestUtils, {shallowMount,mount} from '@vue/test-utils'
import Toolbar from '@/components/Toolbar.vue'


describe('Toolbar.vue', () => {

    it('tests Toolbar.vue', () => {

            const wrapper = shallowMount(Toolbar)
            const vm = wrapper.vm
            expect(vm.theme1).toBe('light');
        }
    )
})
