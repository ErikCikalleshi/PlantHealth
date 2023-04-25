<template>
    <v-app>
        <header-component/>
        <main-container negative>
            <div class="flex flex-wrap">
                <page-heading>Edit User</page-heading>
                <div class="mx-auto my-12 w-full h-full text-black flex-wrap flex">
                    <div class=" w-1/2">
                        <div class="flex-wrap flex align-center">
                            <div class="rounded-full w-64 h-64 object-cover shadow-lg bg">
                                <v-img class="block rounded-full bg object-scale-down"
                                       src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg"></v-img>
                            </div>
                        </div>
                        <v-form ref="addUserForm" >
                            <v-card-text >
                                <!--                                    <v-container>-->
                                <v-row>
                                    <v-col cols="12" sm="6" md="4" class="pl-1">
                                        <v-text-field label="Username" readonly
                                                      :rules="textRules"
                                                      prepend-inner-icon="mdi-account-outline"
                                                      v-model="user.username"
                                        />
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field label="Firstname*" required
                                                      :rules="textRules"
                                                      v-model="user.firstname"/>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4" >
                                        <v-text-field label="Lastname*" required
                                                      :rules="textRules"
                                                      v-model="user.lastname"/>
                                    </v-col>
                                    <v-col cols="12" class="pl-1">
                                        <v-text-field label="Email*" required v-model="user.email"
                                                      prepend-inner-icon="mdi-email-outline"
                                                      :rules="emailRules"/>
                                    </v-col>
                                    <v-col cols="12" sm="6" class="pl-1">
                                        <v-select
                                                clearable
                                                chips
                                                label="Select roles*"
                                                :items="['USER', 'GARDENER', 'ADMIN']"
                                                :rules="[v => v.length>0 || 'Select at least one role']"
                                                v-model="user.roles"
                                                multiple
                                                required
                                                prepend-inner-icon="mdi-account-group-outline"
                                        ></v-select>
                                    </v-col>
                                </v-row>
                                <!--                                    </v-container>-->
                                <small>*indicates required field</small>
                            </v-card-text>
                            <v-card-actions>
                                <!--                                    <v-spacer />-->
                                <v-btn color="error" variant="flat" @click="$router.back()">Cancel</v-btn>
                                <v-btn color="primary" variant="flat" @click="updateUser(user)">Save</v-btn>
                            </v-card-actions>
                            <v-snackbar color="error" v-model="snackbar">E-mail is already in use
                                <template v-slot:actions>
                                    <v-btn color="white" variant="text" @click="snackbar = false">
                                        Close
                                    </v-btn>
                                </template>
                            </v-snackbar>
                        </v-form>
                    </div>
                </div>
            </div>
        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import AdminUserService from "@/services/admin/AdminUserService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import type IUser from "@/interfaces/user/IUser";

export default defineComponent({
    name: "adminEditUser",
    components: {
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
    },
    data() {
        return {
            snackbar: false,
            password: '',
            confirmPassword: '',
            textRules: [
                (v: any) => !!v || 'required',
                (v: any) => (v && v.length <= 20) || 'Max 20 characters',
                (v: any) => !v.match(/^ *$/) || 'No spaces allowed'
            ],
            emailRules: [
                (v: any) => !!v || 'required',
                (v: any) => /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(v) || 'E-mail must be valid'
            ],
            passwordRules: [
                (v: any) => !!v || 'required',
                (v: any) => (v && v.length >= 8) || 'Min 8 characters',
                (v: any) => (v && v.length <= 20) || 'Max 20 characters',
                (v: any) => (v && /[A-Z]/.test(v)) || 'One uppercase letter required',
                (v: any) => (v && /[a-z]/.test(v)) || 'One lowercase letter required',
                (v: any) => (v && /[0-9]/.test(v)) || 'One number required',
            ],
            confirmPasswordRules: [
                (v: any) => !!v || 'required',
                (v: any) => v === this.password || 'Passwords must match'
            ]
        }
    },
    props: {
        username: {
            type: String,
            default: 'default username'
        },
    },
    setup(props) {
        let user = ref<IUser>({username: '', firstname: '', lastname: '', created: '', email: '', roles: []});
        AdminUserService.getSingleUser(props.username).then((response) => {
            user.value = (response.data);
        })
        return {
            user
        }
    },
    methods: {
        toggleUserRole(role: string) {
            console.log(this.user.roles.length)
            if (this.user.roles.includes(role) && this.user.roles.length > 1) {
                this.user.roles = this.user.roles.filter(item => item !== role)
            } else {
                this.user.roles.push(role)
            }
        },
        required(v: any) {
            return !!v || 'Field is required'
        },
        async updateUser(user: IUser) {
            const form = this.$refs.addUserForm as { validate: () => Promise<{ valid: boolean }> };
            const {valid} = await form.validate();
            if (valid) {
                AdminUserService.updateUser(user).then((response) => {
                    if (response.status === 200) {
                        this.$router.back();
                    }
                }).catch((error) => {
                    if (error.response.status === 409) {
                        this.snackbar = true;
                    }
                });
            }
        }
    }
})
</script>