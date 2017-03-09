require 'rspec'

describe "A custom respond_to? on a BasicObject subclass" do
  it "should not try to invoke respond_to_missing? on false result" do
    cls = Class.new(BasicObject) do
      def respond_to?(meth); false; end
    end

    expect(cls.new.respond_to?(:hello)).to eq(false)
  end
end
