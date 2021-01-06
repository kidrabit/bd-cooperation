<script>
  import Layout from "../../layouts/main";
  import PageHeader from "@/components/page-header";
  import appConfig from "@/app.config";
  import Switches from "vue-switches";
  import axios from "axios"

  /**
   * Workspace component
   */
  export default {
    page: {
      title: "Worksapce",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout, Switches, PageHeader},
    data() {
      return {
        title: "Workspace",
        items: [
          {
            text: "Home",
            href: "/"
          },
          {
            text: "Workspace" ,
            active: true
          }
        ],
        form: {
          keyword: "",
          exceptedKeyword: "",
          index: "",
          indices: [],
          exactly: false,
          page: 1
        },
        workspace: {},
        defaultPgSize : 10,
        enabled: [],
        works: {}
      };
    },
    computed: {

    },
    created() {
      this.getWsList()
    },
    methods: {
      getWsList() {
        axios.get(`/wss?size=${this.defaultPgSize}&sort=wsId&page=${this.workspace.number-1}`, {

        }).then(response =>{
          this.workspace = response.data.body
          for(let i = 0; i < this.workspace.content.length; i++) {

            let id = this.workspace.content[i].wsId;
            axios.get(`wss/${id}/works`)
            .then(response=>{
              this.$set(this.works, id, response.data.body)
            })

            if(this.workspace.content[i].userLockYn == 'Y'){
              this.enabled[i] = true
            }else{
              this.enabled[i] = false
            }
          }
          this.workspace.number += 1
          if(response.status == 200) {
            this.getLog()
          }
        })
      },
      deleteWs(id){
        alert("선택하신 Workspace가 삭제됩니다.")
        axios.delete(`wss/${id}`, {

        }).then(response =>{
          if(response.status == 200) {
            this.$router.go(this.$router.currentRoute)
            this.getLog()
          }
        })
      },
      pageChange(){
        this.getWsList()
      },
      toModify(id){
        this.$router.push(`/workspace/modify?id=${id}`)
      },
      toDashboard(id){
        this.$router.push(`/workspace/dashboard?id=${id}`)
      },
      changeLock(wsId, idx){
        this.workspace.content[idx].userLockYn = this.enabled[idx];

        if(this.enabled[idx]){
          this.workspace.content[idx].userLockYn = 'Y'
        }else{
          this.workspace.content[idx].userLockYn = 'N'
        }

        axios.put(`wss/${wsId}/lock`, this.workspace.content[idx])
        .then(response =>{
        })
      },
      getLog() {
        axios.get('wss/log').then(response => {
        })
      }
    }
  };
</script>

<template>
  <Layout>
    <PageHeader :title="title" :items="items"/>
    <div class="text-sm-right">
      <router-link tag="a" to="/workspace/create" class="btn btn-success btn-rounded mb-2 mr-2">
        <i class="mdi mdi-plus mr-1"></i> 새 Workspace 생성
      </router-link>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class>
          <div class="table-responsive">
            <table class="table project-list-table table-nowrap table-centered table-borderless">
              <thead>
              <tr>
                <th style="width: 8%">Workspace 이름</th>
                <th style="width: 8%">Lock</th>
                <th style="width: 35%">작업 상태</th>
                <th style="width: 25%"></th>
                <th style="width: 4%">진척도</th>
                <th style="width: 10%">생성 날짜</th>
                <th style="width: 15%"></th>
              </tr>
              </thead>
              <tbody>

              <tr v-for="(v, idx) in workspace.content" v-bind:key="idx">
                <td>
                  <a href="javascript: void(0);" class="text-dark font-size-14" style="font-weight: bold" @click="toDashboard(v.wsId)">{{v.wsName}}</a>
                </td>
                <td>
                  <switches v-model="enabled[idx]" color="success" class="ml-1" @input="changeLock(v.wsId, idx)"></switches>
                </td>
                <td>
                  <p class="text-muted mb-0 text-dark">마지막 작업 : <span>{{v.lastWork}}</span></p>
                  <p class="text-muted mb-0 text-dark">마지막 작업자 : <span v-if="v.user">{{v.user["user_id"]}}</span></p>
                  <p class="text-muted mb-0 text-dark">Comment : <span>{{v.lastWorkComt}}</span></p>
                </td>
                <td style="">
                  <div style="display: inline-block;" v-for="(work, i) in works[v.wsId]" v-bind:key="i">
                    <div>
                      <img v-if="work.finishYn === 'Y'" src="@/assets/images/workspace/complete.png"  style="width: 20px; height: 20px;"/>
                      <img v-else src="@/assets/images/workspace/incomplete.png"  style="width: 20px; height: 20px;"/>
                      <img v-if="work.worksMultiId.workId != 3" src="@/assets/images/workspace/horizontally-lines.png" style="width: 40px; height: 20px;"/>
                    </div>
                    <div class="font-size-11" v-if="work.worksMultiId.workId === 0">수집</div>
                    <div class="font-size-11" v-if="work.worksMultiId.workId === 1">전처리</div>
                    <div class="font-size-11" v-if="work.worksMultiId.workId === 2">META</div>
                    <div class="font-size-11" v-if="work.worksMultiId.workId === 3">시각화</div>
                  </div>
                </td>
                <td>
                  <span>{{v.percent}}</span><span>%</span>
                </td>
                <td>{{v.wsCrtDt}}</td>
                <td>
                  <div class="button-items">

                    <b-button pill variant="warning" @click="toModify(v.wsId)">수정</b-button>
                    <b-button pill variant="danger" @click="deleteWs(v.wsId)">삭제</b-button>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <b-pagination
            prev-text="이전"
            next-text="다음"
            align="center"
            v-model="workspace.number"
            :total-rows="workspace.totalElements"
            :per-page="10"
            @input="pageChange"></b-pagination>
      </div>
    </div>
    <!-- end row -->
  </Layout>
</template>
