<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('storage:storageviewgoods:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('storage:storageviewgoods:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="goodsid"
        header-align="center"
        align="center"
        label="">
      </el-table-column>
      <el-table-column
        prop="userid"
        header-align="center"
        align="center"
        label="用户id">
      </el-table-column>
      <el-table-column
        prop="goodsname"
        header-align="center"
        align="center"
        label="物品名称">
      </el-table-column>
      <el-table-column
        prop="owername"
        header-align="center"
        align="center"
        label="所有者名字">
      </el-table-column>
      <el-table-column
        prop="picture"
        header-align="center"
        align="center"
        label="物品图片">
      </el-table-column>
      <el-table-column
        prop="location"
        header-align="center"
        align="center"
        label="物品收纳位置">
      </el-table-column>
      <el-table-column
        prop="hotpointid"
        header-align="center"
        align="center"
        label="热点id">
      </el-table-column>
      <el-table-column
        prop="typeid"
        header-align="center"
        align="center"
        label="物品类型">
      </el-table-column>
      <el-table-column
        prop="tag"
        header-align="center"
        align="center"
        label="物品tag">
      </el-table-column>
      <el-table-column
        prop="buytime"
        header-align="center"
        align="center"
        label="购买时间">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="购买价格">
      </el-table-column>
      <el-table-column
        prop="throwtime"
        header-align="center"
        align="center"
        label="丢弃时间">
      </el-table-column>
      <el-table-column
        prop="modifytime"
        header-align="center"
        align="center"
        label="最近修改时间">
      </el-table-column>
      <el-table-column
        prop="goodscomment"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="goodstatus"
        header-align="center"
        align="center"
        label="物品状态">
      </el-table-column>
      <el-table-column
        prop="buildingid"
        header-align="center"
        align="center"
        label="建筑物id">
      </el-table-column>
      <el-table-column
        prop="xstart"
        header-align="center"
        align="center"
        label="热点坐标">
      </el-table-column>
      <el-table-column
        prop="ystart"
        header-align="center"
        align="center"
        label="热点坐标">
      </el-table-column>
      <el-table-column
        prop="xend"
        header-align="center"
        align="center"
        label="热点坐标">
      </el-table-column>
      <el-table-column
        prop="yend"
        header-align="center"
        align="center"
        label="热点坐标">
      </el-table-column>
      <el-table-column
        prop="hotpointname"
        header-align="center"
        align="center"
        label="热点名称">
      </el-table-column>
      <el-table-column
        prop="hotpointcomment"
        header-align="center"
        align="center"
        label="热点描述">
      </el-table-column>
      <el-table-column
        prop="hotpointstatus"
        header-align="center"
        align="center"
        label="状态">
      </el-table-column>
      <el-table-column
        prop="buildingname"
        header-align="center"
        align="center"
        label="建筑物名字">
      </el-table-column>
      <el-table-column
        prop="planpicture"
        header-align="center"
        align="center"
        label="平面图url">
      </el-table-column>
      <el-table-column
        prop="buildingcomment"
        header-align="center"
        align="center"
        label="描述">
      </el-table-column>
      <el-table-column
        prop="typename"
        header-align="center"
        align="center"
        label="类型的显示名称">
      </el-table-column>
      <el-table-column
        prop="parenttypeid"
        header-align="center"
        align="center"
        label="父类型id,如果为空说明是父类型">
      </el-table-column>
      <el-table-column
        prop="typecomment"
        header-align="center"
        align="center"
        label="">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.goodsid)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.goodsid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './storageviewgoods-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/storage/storageviewgoods/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.goodsid
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/storage/storageviewgoods/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
