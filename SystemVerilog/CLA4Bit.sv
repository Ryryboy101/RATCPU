module CLA4Bit (
    input logic [3:0] a,
    b,
    logic cin,
    output logic [3:0] sum,
    logic cout
);
  logic [4:0] c;
  assign c[0] = cin;

  logic [3:0] g = a & b;
  logic [3:0] p = a ^ b;

  always_comb begin : setCarry
    c[1] = g[0] | (p[0] & c[0]);
    c[2] = g[1] | (p[1] & g[0]) | (p[1] & p[0] & c[0]);
    c[3] = g[2] | (p[2] & g[1]) | (p[2] & p[1] & g[0]) | (p[2] & p[1] & p[0] & c[0]);
    c[4] = g[3] | (p[3] & g[2]) | (p[3] & p[2] & g[1]) | (p[3] & p[2] & p[1] & g[0]) | (p[3] & p[2] & p[1] & p[0] & c[0]);
  end
  assign sum  = p ^ c[3:0];
  assign cout = c[4];
endmodule
