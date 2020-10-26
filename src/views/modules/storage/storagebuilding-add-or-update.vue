<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <!-- <el-form-item label="用户id" prop="userid">
      <el-input v-model="dataForm.userid" placeholder="用户id"></el-input>
    </el-form-item> -->
    <el-form-item label="建筑物名字" prop="buildingname">
      <el-input v-model="dataForm.buildingname" placeholder="建筑物名字"></el-input>
    </el-form-item>
    <el-form-item label="平面图url" prop="planpicture">
      <el-input v-model="dataForm.planpicture" placeholder="平面图url"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="comment">
      <el-input v-model="dataForm.comment" placeholder="描述"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          buildingid: 0,
          // userid: '',
          buildingname: '',
          planpicture: '',
          comment: '',
          status: '0'
        },
        dataRule: {
          // userid: [
          //   { required: true, message: '用户id不能为空', trigger: 'blur' }
          // ],
          buildingname: [
            { required: true, message: '建筑物名字不能为空', trigger: 'blur' }
          ],
          planpicture: [
            { required: false, message: '平面图url不能为空', trigger: 'blur' }
          ],
          comment: [
            { required: false, message: '描述不能为空', trigger: 'blur' }
          ],
          status: [
            { required: false, message: '状态不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.buildingid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.buildingid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagebuilding/info/${this.dataForm.buildingid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                // this.dataForm.userid = data.storageBuilding.userid
                this.dataForm.buildingname = data.storageBuilding.buildingname
                this.dataForm.planpicture = data.storageBuilding.planpicture
                this.dataForm.comment = data.storageBuilding.comment
                this.dataForm.status = data.storageBuilding.status
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagebuilding/${!this.dataForm.buildingid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'buildingid': this.dataForm.buildingid || undefined,
                // 'userid': this.dataForm.userid,
                'buildingname': this.dataForm.buildingname,
                'planpicture': this.dataForm.planpicture,
                'comment': this.dataForm.comment,
                'status': this.dataForm.status
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
