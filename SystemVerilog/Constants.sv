package constants;
  typedef enum logic [2:0] {
    add_op  = 3'b000,  // 000
    sub_op  = 3'b001,  // 001
    xor_op  = 3'b010,  //010
    xnor_op = 3'b011,  //011
    or_op   = 3'b100,  //100
    nor_op  = 3'b101,  //101
    and_op  = 3'b110,  //110
    nand_op = 3'b111   //111
  } ALUSelection;
endpackage
