<template>
  <div>
    <!-- 搜索框 -->
    <div style="padding: 10px 0">
      <el-input
        style="width: 200px"
        placeholder="请输入商品类型"
        suffix-icon="el-icon-search"
        v-model="type"
      ></el-input>

      <el-input
        class="ml-5"
        style="width: 200px"
        placeholder="请输入商品信息"
        suffix-icon="el-icon-search"
        v-model="message"
      ></el-input>

      <el-button @click="load" class="ml-5" type="primary">搜索</el-button>
      <i>
        <el-button @click="reset" class="ml-5" type="warning">重置</el-button>
      </i>
    </div>
    <!-- 搜索框 -->

    <!-- 增加与删除 -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">
        新增
        <i class="el-icon-circle-plus-outline"></i>
      </el-button>

      <el-popconfirm
        class="ml-5"
        confirm-button-text="確定"
        cancel-button-text="我在想想"
        icon="el-icon-info"
        icon-color="red"
        title="您確定批量删除數據吗？"
        @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">
          批量删除
          <i class="el-icon-remove-outline"></i>
        </el-button>
      </el-popconfirm>
    </div>

    <el-table
      :data="tableData"
      border
      stripe
      :header-cell-class-name="'headerBg'"
      @selection-change="handleSelectionChange"
    >
      >
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="ID" width="80"></el-table-column>

      <el-table-column prop="type" label="类型" width="200"></el-table-column>

      <el-table-column prop="message" label="信息" width="200"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="success">
            编辑
            <i class="el-icon-edit"></i>
          </el-button>

          <!-- 刪除確認框 -->
          <el-popconfirm
            class="ml-5"
            confirm-button-text="確定"
            cancel-button-text="我在想想"
            icon="el-icon-info"
            icon-color="red"
            title="您確定删除吗？"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">
              删除
              <i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="100px" size="smail">
        <el-form-item label="类型">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="信息">
          <el-input v-model="form.message" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      shop: "",
      message: "",
      type: "",
      dialogFormVisible: false, //默认不展示
      form: {},
      multipleSelection: [] //批量刪除
    };
  },
  created() {
    //请求分页查询数据
    this.load();
  },
  methods: {
    load() {
      this.request
        .get("/type/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            type: this.type,
            message: this.message,
            shop: this.shop
          }
        })
        .then(res => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },

    save() {
      //增加用户
      this.request.post("/type", this.form).then(res => {
        if (res.code === "200") {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;

          this.load(); //刷新数据
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    handleAdd() {
      //增加用户
      this.dialogFormVisible = true;
      this.form = {};
    },

    handleEdit(row) {
      //修改用户
      this.form = row;
      this.dialogFormVisible = true;
    },

    del(id) {
      //删除用户
      this.request.delete("/type/" + id).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("删除失败");
        }
      });
    },

    handleSelectionChange(val) {
      //批量刪除
      console.log(val);
      this.multipleSelection = val;
    },
    delBatch() {
      //批量刪除
      let ids = this.multipleSelection.map(v => v.id);
      this.request.post("/type/del/batch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    reset() {
      ((this.type = ""), (this.message = ""), (this.shop = "")), this.load();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    }
  }
};
</script>

<style>
.headerBg {
  background-color: #eee !important;
}
</style>
